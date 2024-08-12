@echo off
setlocal enabledelayedexpansion

REM Define the strings to be replaced and their replacements
set "search=_enchantment"
set "replace=_talisman"

REM Loop through all files in the current directory and subdirectories
for /r %%f in (*) do (
    set "filename=%%~nxf"
    set "newfilename=!filename:%search%=%replace%!"
    
    REM Only rename if the new filename is different
    if not "!filename!"=="!newfilename!" (
        echo Renaming "%%f" to "%%~dpf!newfilename!"
        ren "%%f" "!newfilename!"
    )
)

echo Rename operation complete.
pause
