LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_SRC_FILES:= server.cpp main.cpp

LOCAL_SHARED_LIBRARIES := libutils libbinder

LOCAL_C_INCLUDES := $(BINDERDEMO_C_INCLUDES)


LOCAL_LDLIBS += -llog

LOCAL_MODULE:= binderTSer
LOCAL_CFLAGS += -pie -fPIE
LOCAL_LDFLAGS += -pie -fPIE

include $(BUILD_EXECUTABLE)
