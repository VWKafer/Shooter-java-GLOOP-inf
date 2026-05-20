
#include "twoMouse.h"
#include "pch.h"
#include <atomic>
#include <Windows.h>
#include <iostream>
#include <vector>
#include <mutex>
#include <map>
#include <sstream>
#include <deque>
#include <objbase.h>
using namespace std;

// State
bool isInitialized = false;
HANDLE runningThread = 0;
HWND messageWindow = 0;

// Multi threading
using Mutex = std::mutex;
using MutexLocker = std::lock_guard<std::mutex>;
#define EnsureMutexLocked(T) { if(T.try_lock()) Crash(); }
#define Crash() { std::cout << "CRASH()"; ((void(*)())0)(); }
#define StrongAssert(T) { if(!(T)) cout << "Assertion Failed" << ##T << "\n"; Crash(); }

// RawInput stuff
const uint8_t RE_DEVICE_CONNECT = 0;
const uint8_t RE_DEVICE_DISCONNECT = 1;
const uint8_t RE_MOUSE = 2;

///
///
/// 
/// ////////////////////////////von 
/// mouse 1
static int M1X = 0;
static int M1Y = 0;
static bool M1R = false;
static bool M1L = false;
static int M1devHandle = -1;
// mouse 2
static int M2X = 0;
static int M2Y = 0;
static bool M2R = false;
static bool M2L = false;
static int M2devHandle = -1;
/// //////////////////////bis
/// 
/// 
/// 
struct RawInputEvent
{
    int32_t devHandle;
    int32_t x, y, wheel;
    // pressed button
    uint8_t press;
    //released button
    uint8_t release;
    // event type
    uint8_t type;
};
vector<RawInputEvent> generatedEvents; // deltas must be accumulated
Mutex dataMutex;

// Mouse state for now
struct DeviceState
{
    int32_t x, y, z;    // x,y are delts movement, z is WheelDelta
    uint8_t buttonStates;   // bad idea for polling
    wstring name;
};

// Global buffer for worker thread
std::vector<char> m_RawInputMessageData; // Buffer

map<HANDLE, DeviceState> devices;

/////////// von

int getX(int device) {
    cout << "got x "<< M1X;
    if (device == 1) {
        return M1X;
    }
    else {
        
        return M2X;
    }
}

int getY(int device) {
    if (device == 1) {
        return M1Y;
    }
    else {

        return M2Y;
    }
}

bool getTaste(int device, char taste) {
    if (device == 1) {
        if (taste == 'R')
        {
            return M1R;
        }
        if (taste == 'L')
        {
            return M1L;
        }
    }
    else {

        if (taste == 'R')
        {
            return M2R;
        }
        if (taste == 'L')
        {
            return M2L;
        }
    }
}
//////// bis


inline void AddEvent(uint8_t type, int32_t devHandle, uint8_t press, uint8_t release)
{
    MutexLocker locker(dataMutex);
    RawInputEvent e;
    e.x = 0;
    e.y = 0;
    e.wheel = 0;
    e.type = type;
    e.devHandle = devHandle;
    cout << devHandle;
    e.press = press;
    e.release = release;
    ////////////////von
    if (e.devHandle== M1devHandle)
    {
        if (press == 1) {
            M1L = true;
        }
        if (press == 2) {
            M1R = true;
        }
    }

    if(e.devHandle ==M2devHandle)
    {
        if (press == 1) {
            M2L = true;
        }
        if (press == 2) {
            M2R = true;
        }
    }
    /////////////bis
    generatedEvents.push_back(e);

}

inline void AddEvent(RawInputEvent& ev)
{
    MutexLocker locker(dataMutex);
    generatedEvents.push_back(ev);

}

void OnRawInput(HRAWINPUT handle)
{
    // Determine the size
    UINT dataSize;
    GetRawInputData(handle, RID_INPUT, NULL, &dataSize, sizeof(RAWINPUTHEADER));    // get Size
    if (dataSize == 0) return;
    if (dataSize > m_RawInputMessageData.size()) m_RawInputMessageData.resize(dataSize);

    // Get the Data
    void* dataBuf = &m_RawInputMessageData[0];
    GetRawInputData(handle, RID_INPUT, dataBuf, &dataSize, sizeof(RAWINPUTHEADER)); // get Data
    const RAWINPUT* raw = (const RAWINPUT*)dataBuf;

    // Mouse
    //if (raw->header.dwType == RIM_TYPEMOUSE)

    HANDLE deviceHandle = raw->header.hDevice;
    const RAWMOUSE& mouseData = raw->data.mouse;

    USHORT flags = mouseData.usButtonFlags;
    short wheelDelta = (short)mouseData.usButtonData;
    LONG x = mouseData.lLastX, y = mouseData.lLastY;

    // Some events are critical
    if (flags & RI_MOUSE_LEFT_BUTTON_DOWN) AddEvent(RE_MOUSE, int32_t(raw->header.hDevice), 1, 0);
    if (flags & RI_MOUSE_LEFT_BUTTON_UP) AddEvent(RE_MOUSE, int32_t(raw->header.hDevice), 0, 1);
    if (flags & RI_MOUSE_MIDDLE_BUTTON_DOWN) AddEvent(RE_MOUSE, int32_t(raw->header.hDevice), 3, 0);
    if (flags & RI_MOUSE_MIDDLE_BUTTON_UP) AddEvent(RE_MOUSE, int32_t(raw->header.hDevice), 0, 3);
    if (flags & RI_MOUSE_RIGHT_BUTTON_DOWN) AddEvent(RE_MOUSE, int32_t(raw->header.hDevice), 2, 0);
    if (flags & RI_MOUSE_RIGHT_BUTTON_UP) AddEvent(RE_MOUSE, int32_t(raw->header.hDevice), 0, 2);


    // Some are to be accumulated
    auto& dev = devices[raw->header.hDevice];
    dev.x += x;
    dev.y += y;
    dev.z += wheelDelta;
}

void OnDeviceChange(HRAWINPUT handle, bool connected)
{
    if (!connected)
    {
        RawInputEvent ev;
        ev.devHandle = int32_t(handle);
        ev.type = connected ? RE_DEVICE_CONNECT : RE_DEVICE_DISCONNECT;
        ev.x = 0;
        ev.y = 0;
        AddEvent(ev);
        MutexLocker locker(dataMutex);
        devices.erase(handle);
        return;
    }

    // Determine the size, Get Device Name
    std::vector<wchar_t> deviceNameData;
    wstring deviceName;
    UINT dataSize;
    SetLastError(0);
    GetRawInputDeviceInfo(handle, RIDI_DEVICENAME, nullptr, &dataSize);
    if (GetLastError()) return;
    if (dataSize)
    {
        deviceNameData.resize(dataSize);
        UINT result = GetRawInputDeviceInfo(handle, RIDI_DEVICENAME, &deviceNameData[0], &dataSize);
        if (result != UINT_MAX)
        {
            deviceName.assign(deviceNameData.begin(), deviceNameData.end());
            wprintf(L"  Name=%s\n", deviceName.c_str());
        }
    }

    RID_DEVICE_INFO deviceInfo;
    deviceInfo.cbSize = sizeof deviceInfo;
    dataSize = sizeof deviceInfo;
    UINT result = GetRawInputDeviceInfo(handle, RIDI_DEVICEINFO, &deviceInfo, &dataSize);
    if (result != UINT_MAX)
    {
        wprintf(L"  Id=%u, Buttons=%u, SampleRate=%u, HorizontalWheel=%s\n",
            deviceInfo.mouse.dwId,
            deviceInfo.mouse.dwNumberOfButtons,
            deviceInfo.mouse.dwSampleRate,
            deviceInfo.mouse.fHasHorizontalWheel ? L"1" : L"0");

        // At this perfect moment, add OR remove the device
        RawInputEvent ev;
        ev.devHandle = int32_t(handle);
        ev.type = RE_DEVICE_CONNECT;
        ev.x = 0;
        ev.y = 0;
        AddEvent(ev);
        MutexLocker locker(dataMutex);
        devices[handle].name = deviceName;
    }
}

LRESULT CALLBACK RawInputWndProc(HWND wh, UINT msg, WPARAM wp, LPARAM lp)
{
    // Debugging Message pumps
    //cout << WMMessageToStr(msg, true) << ":  W= " << wp << ";  L= " << lp << "\n";

    // 254: WM_INPUT_DEVICE_CHANGE
    //      wp = 1 GIDC_ARRIVAL
    //      wp = 2 GIDC_REMOVAL

    // 255: WM_INPUT

    if (msg == WM_INPUT_DEVICE_CHANGE)
    {
        if (wp == 1)
        {
            OnDeviceChange((HRAWINPUT)lp, true);
        }
        else if (wp == 2)
        {
            OnDeviceChange((HRAWINPUT)lp, false);
        }
    }
    else if (msg == WM_INPUT)
    {
        OnRawInput((HRAWINPUT)lp);
    }

    return DefWindowProc(wh, msg, wp, lp);
}

static const wchar_t* class_name = L"PI_DEV_RAWINPUT";
void RawInputThread(LPVOID params)
{
    WNDCLASSEX wx = {};
    wx.cbSize = sizeof(WNDCLASSEX);
    wx.lpfnWndProc = RawInputWndProc;
    wx.hInstance = NULL;
    wx.lpszClassName = class_name;
    HWND wh;
    if (RegisterClassEx(&wx))
    {
        wh = CreateWindowEx(0, class_name, L"Pi-Dev RawInput [NS]", 0, 0, 0, 0, 0, HWND_DESKTOP, NULL, NULL, NULL);
        messageWindow = wh;
        ShowWindow(wh, SW_SHOWMINNOACTIVE);

        RAWINPUTDEVICE device[4];

        // Mouse
        device[0].usUsagePage = 0x01;
        device[0].usUsage = 0x02;
        device[0].dwFlags = RIDEV_INPUTSINK | RIDEV_DEVNOTIFY;
        device[0].hwndTarget = wh;

        // Gamepad
        device[1].usUsagePage = 0x01;
        device[1].usUsage = 0x05;
        device[1].dwFlags = RIDEV_INPUTSINK | RIDEV_DEVNOTIFY;
        device[1].hwndTarget = wh;

        // Joystick
        device[2].usUsagePage = 0x01;
        device[2].usUsage = 0x04;
        device[2].dwFlags = RIDEV_INPUTSINK | RIDEV_DEVNOTIFY;
        device[2].hwndTarget = wh;

        // Keyboard
        device[3].usUsagePage = 0x01;
        device[3].usUsage = 0x06;
        device[3].dwFlags = RIDEV_INPUTSINK | RIDEV_DEVNOTIFY;
        device[3].hwndTarget = wh;

        // Register ONLY Mice
        RegisterRawInputDevices(device, 1, sizeof RAWINPUTDEVICE);


        MSG msg;
        while (GetMessage(&msg, 0, 0, 0) > 0)
        {
            DispatchMessage(&msg);
        }
    }
}

int kill()
{
    SetLastError(0);
    PostThreadMessage(GetThreadId(runningThread), WM_QUIT, 0, 0);
    cout << "PostThreadMessage = " << GetLastError() << "\n";

    SetLastError(0);
    UnregisterClass(class_name, NULL);
    cout << "UnregisterClass = " << GetLastError() << "\n";
    /**/

    messageWindow = 0;
    runningThread = 0;
    isInitialized = false;
    return GetLastError();
}

bool init()
{
    kill();
    // this is actually reinit()
    cout << "init()";
    MutexLocker locker(dataMutex);
    devices.clear();
    generatedEvents.clear();

    if (!isInitialized)
    {
        isInitialized = true;
        runningThread = CreateThread(NULL, 0, LPTHREAD_START_ROUTINE(RawInputThread), NULL, 0, 0);
        messageWindow = 0;
        return true;
    }

    return false;
}

void* poll()
{
    MutexLocker locker(dataMutex);
    //cout << "==== Deltas =====\n";
    int numItems = generatedEvents.size();
    stringstream ss;
    int i = 0;///////////////////////////
    for (auto& d : devices)
    {
        RawInputEvent e;
        e.devHandle = int32_t(d.first);
        auto& data = d.second;
        e.press = 0;
        e.release = 0;
        e.type = RE_MOUSE;
        e.wheel = data.z;
        e.x = data.x;
        e.y = data.y;
        if (e.x != 0 || e.y != 0)
        {
            ss.write((char*)&e, sizeof(RawInputEvent));
            ++numItems;
        }

        cout << "dev:" << i << "  ";//////////////////////von

        cout << e.x << "; " << e.y << "\n";
        if (M2devHandle == -1 || M1devHandle == -1) {
            if (e.x != 0 || e.y != 0) {
                if (M1devHandle == -1) {
                    M1devHandle = e.devHandle;

                }
                else {
                    if (M2devHandle == -1 && M1devHandle != e.devHandle) {
                       
                        M2devHandle = e.devHandle;
                    }
                }
            }
        }/////////////////////////////////////////////////bis
        // Zero accumulation fields
        data.x = 0;
        data.y = 0;
        data.z = 0;

        ///////////von
        if (e.devHandle == M1devHandle) {
            M1X = e.x;
            M1Y = e.y;
        }
        if (e.devHandle == M2devHandle)
        {
            
            M2X = e.x;
            M2Y = e.y;
        }
        i++;/////////////////bis
    }
    cout << "\n";
    ss.write((char*)generatedEvents.data(), sizeof(RawInputEvent) * generatedEvents.size());
    uint8_t* buf = (uint8_t*)CoTaskMemAlloc(4 + numItems * sizeof(RawInputEvent));
    memcpy(buf, &numItems, 4);
    memcpy(buf + 4, ss.str().data(), numItems * sizeof(RawInputEvent));
    generatedEvents.clear();
    return buf;
}

void Update() {
    ////////////von

    //cout << " M1X:" << M1X << "  M1Y:" << M1Y << "  M1R:" << M1R << "  M1L;" << M1L << "\n";
    //cout << " M2X:" << M2X << "  M2Y:" << M2Y << "  M2R:" << M2R << "  M2L:" << M2L << "\n";


    M1X = 0;
    M1Y = 0;
    M1R = false;
    M1L = false;
    M2X = 0;
    M2Y = 0;
    M2R = false;
    M2L = false;
    /////////bis

    if (GetAsyncKeyState(VK_HOME)) cout << "init() = " << init() << "\n";
    if (GetAsyncKeyState(VK_END))  cout << "kill() = " << kill() << "\n";
    //Sleep(1000); 
    

    void* data = poll();


    

    int d = 0;
    memcpy(&d, data, 4);
    //cout << d << "\n";
}

