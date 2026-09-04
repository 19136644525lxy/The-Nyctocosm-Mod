function ajnullpoint:animations/stop_all_animations
function ajnullpoint:animations/idle/play
execute positioned ~ ~2 ~ run function sorcerer:attacks/icespikes/wave
playsound nyctocosm:sorcerer.staff.emitwave master @a[distance=..30] ~ ~ ~ 4
particle explosion ~ ~2 ~ 0.5 0.5 0.5 0 10 force