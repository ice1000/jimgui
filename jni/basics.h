//
// Created by ice1000 on 17-5-1.
//

#ifndef JIMGUI_BASICS_H
#define JIMGUI_BASICS_H

#define __JNI__FUNCTION__INIT__ \
jboolean *option = NULL;

#define __JNI__FUNCTION__CLEAN__ \
delete option;

#define __release(type, name) \
env->Release ## type ## ArrayElements(_ ## name, name, JNI_OK);

#define __abort(type, name) \
env->Release ## type ## ArrayElements(_ ## name, name, JNI_ABORT);

#define __get(type, name) \
auto (name) = env->Get ## type ## ArrayElements(_ ## name, option);

#define __new(type, name, len) \
auto _ ## name = env->New ## type ## Array(len);

#define __set(type, name, len) \
env->Set ## type ## ArrayRegion(_ ## name, 0, len, name);

#define __len(name) \
env->GetArrayLength(_ ## name)

#endif //JIMGUI_BASICS_H
