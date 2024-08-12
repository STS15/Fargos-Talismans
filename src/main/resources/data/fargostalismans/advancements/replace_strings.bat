@echo off
setlocal enabledelayedexpansion

REM Define the strings to be replaced and their replacements
set "search=_enchantment"
set "replace=_talisman"

REM Loop through all .json files in the current directory and subdirectories
for /r %%f in (*.json) do (
    REM Read the file and replace the string
    powershell -Command "(Get-Content -path '%%f') -replace '!search!', '!replace!' | Set-Content -path '%%f'"
)

echo Replacement complete.
pause
