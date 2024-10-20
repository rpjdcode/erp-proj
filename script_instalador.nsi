; Ejemplo de Script NSI para una aplicación Java

!define AppName "HostManager"
!define AppVersion "0.0.1.0"
!define AppPublisher "RPJD"
!define AppURL "http://www.miwebsite.com"
!define AppExeName "HostManager.exe"
!define AppIcon "A:\Evi\Workspace-Eclipse2024\HostManager\src\main\resources\icon.ico"
!define AppLibDir "lib"
!define AppLogDir "log"
!define AppJarDir "jar"
!define AppEnvVar "HMRP"
!define MUI_ICON "A:\Evi\Workspace-Eclipse2024\HostManager\src\main\resources\icon.ico"
; Icono del desinstalador aún por decidir
;!define MUI_UNICON "A:\Evi\Workspace-Eclipse2024\MasterRP\src\main\resources\icono.ico"

; Configuraciones de instalación
Outfile "target/${AppName}Installer_${AppVersion}.exe"
Name "${AppName} ${AppVersion}"

InstallDir "$PROGRAMFILES\${AppName}"
InstallDirRegKey HKLM "Software\${AppName}" "Install_Dir"
; Solicitar elevación de permisos de administrador
RequestExecutionLevel admin ; <--- Esta línea solicita permisos de administrador

; Incluir la biblioteca de funciones para desinstalación
!include "MUI2.nsh"

; Páginas del instalador
!insertmacro MUI_PAGE_WELCOME
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_INSTFILES
!insertmacro MUI_PAGE_FINISH

; Páginas del desinstalador
!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES

; Configuración de idioma
!insertmacro MUI_LANGUAGE "Spanish"

Section
    ; Check for write access
	EnVar::Check "NULL" "NULL"
	Pop $0
	DetailPrint "EnVar::Check write access HKCU returned=|$0|"
    ; Añade tu ruta a la variable de entorno PATH del usuario
	EnVar::AddValue "${AppEnvVar}" "$INSTDIR"
	Pop $0
	DetailPrint "EnVar::AddValue returned=|$0|"
	
SectionEnd

; Secciones del instalador
Section "Install"

  SetOutPath "$INSTDIR"
  ; Copia el archivo ejecutable de tu aplicación
  File "A:\Evi\Workspace-Eclipse2024\HostManager\target\${AppExeName}"
  
  ; Copiar JAR de aplicación a $INSTDIR\jar
  SetOutPath "$INSTDIR\${AppJarDir}"
  File /r "A:\Evi\Workspace-Eclipse2024\HostManager\target\jar\*.*"
  
  ; Copiar todas las dependencias a $INSTDIR\jar\lib
  SetOutPath "$INSTDIR\${AppJarDir}\${AppLibDir}"
  File /r "A:\Evi\Workspace-Eclipse2024\HostManager\target\lib\*.*"
  
  ; Crea un acceso directo en el menú de inicio
  CreateDirectory "$SMPROGRAMS\${AppName}"
  CreateShortCut "$SMPROGRAMS\${AppName}\${AppName}.lnk" "$INSTDIR\${AppExeName}"
  ; Escribe en el registro para soportar la desinstalación
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${AppName}" "DisplayName" "${AppName} - ${AppVersion}"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${AppName}" "UninstallString" "$\"$INSTDIR\uninstall.exe$\""
  WriteRegStr HKLM "Software\${AppName}" "Install_Dir" "$INSTDIR"
  ; Genera el ejecutable del desinstalador
  WriteUninstaller "$INSTDIR\uninstall.exe"
SectionEnd

; Sección de desinstalación
Section "Uninstall"
  ; Elimina archivos y directorios
  Delete "$INSTDIR\${AppExeName}"
  RMDir /r "$INSTDIR\${AppJarDir}\${AppLibDir}"
  RMDir /r "$INSTDIR\${AppLogDir}"
  
  RMDir "$INSTDIR"
  Delete "$SMPROGRAMS\${AppName}\${AppName}.lnk"
  RMDir "$SMPROGRAMS\${AppName}"
  
  ; Limpia el registro
  DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${AppName}"
  DeleteRegKey HKLM "Software\${AppName}"
  
  ; Eliminar variable de entorno  ; Delete a value from a variable
  EnVar::DeleteValue "${AppEnvVar}" "$INSTDIR"
  Pop $0
  DetailPrint "EnVar::DeleteValue returned=|$0|"
  
  EnVar::Delete "${AppEnvVar}"
  Pop $0
  DetailPrint "EnVar::Delete returned=|$0|"
SectionEnd
