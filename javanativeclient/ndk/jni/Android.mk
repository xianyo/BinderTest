
LOCAL_PATH:= $(call my-dir)

BINDERDEMO_C_INCLUDES := $(LOCAL_PATH)/include \
    $(LOCAL_PATH)/libextandroid/include/ 


include $(call all-makefiles-under,$(LOCAL_PATH))

