#!/usr/bin/python
# -*- coding: UTF-8 -*-

import sys
import re

function_module="\n\
\t@JsonRpcMethod(Method = @Json_method) \n\
\tdefault @re_value @function_name(@pp_list, Object res) throws JsonRpcException {\n\
\t        return (@re_value) res;\n\
\t}"

#m=appUserRegisterMobile(mobile:String, code:String)->String

function_context=""
package_name="package_name"
class_name="class_name"
function_list = []

def functionToString(function):
    global function_context
    group = re.match(r'(.+)\((.+)\)->(.+)',function)
    if group:
        function_str=function_module
        function_name=group.group(1)
        pp_list=group.group(2)
        re_value=group.group(3)
        print("function_name: "+function_name)
        print("pp_list: "+str(pp_list))
        print("re_value: "+re_value)
        function_str=function_str.replace("@re_value",re_value)
        function_str=function_str.replace("@function_name",function_name)
        function_str=function_str.replace("@pp_list",pp_list)
        pp_list=pp_list.split(",")
        for pp in pp_list:
            pp=pp.lstrip()
            pp=pp.rstrip()
            function=function.replace(pp.split(" ")[1],"")
        function=function.replace(" ","")
        function_str=function_str.replace("@Json_method","\"{0}\"".format(function))
        function_context=function_context+function_str+"\n"


def main():
    file_name = "jsonrpc.txt"
    file_name_tag = False
    for arg in sys.argv:
        if "-f" == arg:
            file_name_tag = True
        if file_name_tag:
            file_name = arg
    print("file_name", file_name)
    with open(file_name, "r") as f:
        for line in f.readlines():
            if line.startswith("package="):
                package_name=line[len("package="):-1]
            if line.startswith("class="):
                class_name=line[len("class="):-1]
            if line.startswith("m="):
                function_list.append(line[len("m="):-1])
        for function in function_list:
            print(functionToString(function))

    res=""
    with open("module", "r") as f:
        res=f.read()

    with open(class_name+".java", "w") as f:
        res=res.replace("@package",package_name)
        res=res.replace("@class",class_name)
        res=res.replace("@m",function_context.replace("Dict","JsonObject"))
        f.write(res)

if __name__ == '__main__':
    main()
