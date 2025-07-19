
import sounddevice as sd
import speech_recognition as sr
from pycaw.pycaw import AudioUtilities, IAudioEndpointVolume
from ctypes import cast, POINTER
from comtypes import CLSCTX_ALL


# Diccionario de malas palabras
malas_palabras = ["gasolina", "clase", "mala_palabra3"]

# Función para bajar el volumen
def bajar_volumen():
    devices = AudioUtilities.GetSpeakers()
    interface = devices.Activate(IAudioEndpointVolume._iid_, CLSCTX_ALL, None)
    volume = cast(interface, POINTER(IAudioEndpointVolume))
    current_volume = volume.GetMasterVolumeLevel()
    new_volume = max(current_volume - 10.0, -65.0)
    volume.SetMasterVolumeLevel(new_volume, None)
    print(f"Volumen bajado a: {new_volume}")

# Función para restaurar el volumen
def restaurar_volumen():
    devices = AudioUtilities.GetSpeakers()
    interface = devices.Activate(IAudioEndpointVolume._iid_, CLSCTX_ALL, None)
    volume = cast(interface, POINTER(IAudioEndpointVolume))
    current_volume = volume.GetMasterVolumeLevel()
    new_volume = min(current_volume + 10.0, 0.0)
    volume.SetMasterVolumeLevel(new_volume, None)
    print(f"Volumen restaurado a: {new_volume}")

# Función para detectar malas palabras
def detectar_malas_palabras(texto):
    print(f"Texto procesado: {texto}")
    for palabra in malas_palabras:
        if palabra in texto.lower():
            print(f"Palabra detectada: {palabra}")
            return True
    return False

# Función para procesar el audio y detectar malas palabras
def procesar_audio(indata, frames, time, status):
    r = sr.Recognizer()
    if status:
        print(f"Status: {status}")

    try:
        audio_data = sr.AudioData(indata.tobytes(), 44100, 1)
        print("Procesando audio...")

        texto = r.recognize_google(audio_data, language="es-ES")
        print(f"Texto detectado: {texto}")

        if detectar_malas_palabras(texto):
            print("Mala palabra detectada, bajando volumen...")
            bajar_volumen()
            time.sleep(3)  # Mantiene el volumen bajo por 3 segundos
            restaurar_volumen()
        else:
            print("No se detectaron malas palabras.")

    except sr.UnknownValueError:
        print("No se pudo entender el audio.")
    except sr.RequestError as e:
        print(f"Error al conectar con el servicio de reconocimiento de voz: {e}")

# Capturar el audio del sistema y analizarlo
try:
    with sd.InputStream(callback=procesar_audio, channels=1, dtype="float32", device=1, blocksize=2048):  # Aumentar blocksize
        print("Escuchando en tiempo real... (presiona Ctrl+C para detener)")
        while True:
            sd.sleep(1000)

except Exception as e:
    print(f"Ocurrió un error: {e}")
