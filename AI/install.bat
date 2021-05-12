@echo off
@chcp 65001 1> NUL 2> NUL
setlocal enableextensions enabledelayedexpansion
pushd %~dp0

:BatchGotAdmin
:-------------------------------------
REM  --> Check for permissions
>nul 2>&1 "%SYSTEMROOT%\system32\cacls.exe" "%SYSTEMROOT%\system32\config\system"

REM --> If error flag set, we do not have admin.
if '%errorlevel%' NEQ '0' (
    echo Requesting administrative privileges...
    goto UACPrompt
) else ( goto gotAdmin )

:UACPrompt
    echo Set UAC = CreateObject^("Shell.Application"^) > "%temp%\getadmin.vbs"
    echo UAC.ShellExecute "%~s0", "", "", "runas", 1 >> "%temp%\getadmin.vbs"

    "%temp%\getadmin.vbs"
    exit /B

:gotAdmin
    if exist "%temp%\getadmin.vbs" ( del "%temp%\getadmin.vbs" )
    pushd "%CD%"
    CD /D "%~dp0"
:--------------------------------------
title INSTALL REQUIREMENTS

set root=C:\ProgramData\Miniconda3
call %root%\Scripts\activate.bat %root%

REM echo .
echo 기존 설치된 라이브러리 및 모듈 초기화
call conda env remove -n AI
REM call conda clean -a -y
REM call conda install --revision 0 -y

echo .
echo 가상환경 "AI"를 생성하고, 파이썬 3.8로 설치
call conda create -n AI python=3.8 -y
call conda activate AI

REM echo .
REM echo 파이썬이 3.7로 설치되어 있지 않다면 개발자에게 이슈를 달아주세요
REM call conda list python | find "python"

echo .
echo 라이브러리 및 모듈 설치 시작
REM  call conda install -c conda-forge dlib -y
call pip install -r requirements.txt

REM  echo .
REM  echo pytorch 설치 시작
REM call conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud/pytorch/
REM call conda install pytorch torchvision torchaudio cudatoolkit=11.2 -c pytorch -y
REM  call conda install pytorch torchvision torchaudio cudatoolkit=10.1 -c pytorch -y --channel https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud/pytorch/

echo .
echo 설치가 완료되었습니다. run.bat 파일로 서버를 실행하시면 됩니다.
pause
