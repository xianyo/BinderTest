BUILD_LIBRARY := yes

ifneq ($(strip $(BUILD_LIBRARY)),yes)
LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_SRC_FILES:= client.cpp main.cpp

LOCAL_SHARED_LIBRARIES := libutils libbinder 
LOCAL_C_INCLUDES := $(BINDERDEMO_C_INCLUDES)

LOCAL_LDLIBS += -llog

LOCAL_MODULE:= binderTClient 
LOCAL_CFLAGS += -pie -fPIE 
LOCAL_LDFLAGS += -pie -fPIE

include $(BUILD_EXECUTABLE)

else

LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_SRC_FILES:= client.cpp 

LOCAL_SHARED_LIBRARIES := libutils libbinder 
LOCAL_C_INCLUDES := $(BINDERDEMO_C_INCLUDES)

LOCAL_LDLIBS += -llog

LOCAL_MODULE:= BinderClient

include $(BUILD_SHARED_LIBRARY)

endif