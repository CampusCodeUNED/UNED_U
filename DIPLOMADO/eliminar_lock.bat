@echo off
setlocal

set REPO_PATH="C:\Users\Personal\Desktop\GENERADOR DE EXAMEN\GENERADORES_UNIVERSIDAD\II_EXAMEN_MATEMATICA_FINANCIERA\.git"
set LOCK_FILE=%REPO_PATH%\index.lock

if exist %LOCK_FILE% (
    echo Eliminando archivo de bloqueo: index.lock
    del /f %LOCK_FILE%
    echo Archivo eliminado correctamente.
) else (
    echo No se encontró el archivo index.lock.
)

pause
endlocal
