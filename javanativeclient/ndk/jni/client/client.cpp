#define LOG_TAG "native_client"
#include "api.h"
#include <jni.h>

using namespace android;
using namespace demo_api;

/*
 * ---------------------------------------------------------------------------
 *
 *  Client Proxy
 */
namespace demo_api {

    BpDemoAPI::BpDemoAPI(const sp<IBinder>& impl ):BpInterface<IDemoAPI>(impl)
    {

    }

    char* BpDemoAPI::getName()
    {
        Parcel data, reply;
        data.writeInterfaceToken(IDemoAPI::getInterfaceDescriptor());
        //By operation code to transact
        remote()->transact(GET_NAME, data, &reply);
        //Exception Code. In Java Level, aidl auto generate codes will process exceptioncode.
        reply.readExceptionCode();
        return (char*)reply.readCString();
    }

    String16* BpDemoAPI::getFullName(String16* part)
    {
        Parcel data, reply;
        data.writeInterfaceToken(IDemoAPI::getInterfaceDescriptor());
        data.writeString16(*part);
        remote()->transact(GET_FULL_NAME, data, &reply);
        reply.readExceptionCode();
        String16* s = new String16(reply.readString16());
        return s;
    }

    int BpDemoAPI::sum(int a, int b)
    {
        Parcel data, reply;
        data.writeInterfaceToken(IDemoAPI::getInterfaceDescriptor());
        data.writeInt32(a);
        data.writeInt32(b);
        remote()->transact(SUM, data, &reply);
        reply.readExceptionCode();
        return (int)reply.readInt32();
    }

    IMPLEMENT_META_INTERFACE(DemoAPI,META_INTERFACE_NAME);

}//end of namespace demo_api
/*
 * End of Client Proxy
 */


extern "C" {

JNIEXPORT jstring JNICALL Java_com_zhuxy_javanativeclient_MainActivity_getName
  (JNIEnv *env, jobject obj)
{
    jstring ret;
    char *name;
    int cnt = 0;
    sp<IBinder> binder;
    sp<ProcessState> proc(ProcessState::self());
    //get service manager
    sp<IServiceManager> sm = defaultServiceManager();
    ALOGD("getName....."); 
    do{
        //Search service by SERVICE_NAME
        binder = sm->getService(String16(SERVICE_NAME));
        if(binder != 0)
            break;
        sleep(1);
    }while(cnt++<3);
    if(binder==0){
        ALOGE("binder err....."); 
        return NULL;
    }
    const sp<IDemoAPI>& bts = interface_cast<IDemoAPI>(binder);
    ALOGD("bindertest client is starting....."); 
    name = bts->getName();
    ALOGD("Service Name=%s",name);
    return env->NewStringUTF(name);
}

JNIEXPORT jstring JNICALL Java_com_zhuxy_javanativeclient_MainActivity_getFullName
  (JNIEnv *env, jobject obj, jstring str)
{
    jstring ret=NULL;
    const char *name;
    const String16* full;
    int cnt = 0;
    sp<IBinder> binder;
    sp<ProcessState> proc(ProcessState::self());
    //get service manager
    sp<IServiceManager> sm = defaultServiceManager();
    ALOGD("getFullName....."); 
    do{
        //Search service by SERVICE_NAME
        binder = sm->getService(String16(SERVICE_NAME));
        if(binder != 0)
            break;
        sleep(1);
    }while(cnt++<3);
    if(binder==0){
        ALOGE("binder err....."); 
        return NULL;
    }
    const sp<IDemoAPI>& bts = interface_cast<IDemoAPI>(binder);
    ALOGD("bindertest client is starting....."); 
    const char *partStr = env->GetStringUTFChars(str, NULL);
    String16* str16 = new String16(partStr);
    env->ReleaseStringUTFChars(str, partStr);
    full = bts->getFullName(str16);
    name = String8(*full).string();
    ALOGD("Service FullName=%s",name);
    return env->NewStringUTF(name);
}

JNIEXPORT jint JNICALL Java_com_zhuxy_javanativeclient_MainActivity_sum
  (JNIEnv *env, jobject obj, jint para1, jint para2)
{
    jint ret;
    int cnt = 0;
    sp<IBinder> binder;
    sp<ProcessState> proc(ProcessState::self());
    //get service manager
    sp<IServiceManager> sm = defaultServiceManager();
    ALOGD("sum....."); 
    do{
        //Search service by SERVICE_NAME
        binder = sm->getService(String16(SERVICE_NAME));
        if(binder != 0)
            break;
        sleep(1);
    }while(cnt++<3);
    if(binder==0){
        ALOGE("binder err....."); 
        return -1;
    }
    const sp<IDemoAPI>& bts = interface_cast<IDemoAPI>(binder);
    ALOGD("bindertest client is starting....."); 
    ret = bts->sum(para1,para2);
    ALOGD("Service SUM %d+%d=%d",para1,para2,ret);
    return ret;
}

}
