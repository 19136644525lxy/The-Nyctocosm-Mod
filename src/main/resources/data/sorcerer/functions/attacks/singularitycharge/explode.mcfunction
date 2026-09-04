#built using mc-build (https://github.com/mc-build/mc-build)

particle explosion_emitter ~ ~ ~ 0 0 0 0 1 force
execute as @a[distance=..5] run function sorcerer:attacks/singularitycharge/hit
playsound nyctocosm:sorcerer.singularitycharge.explode master @a[distance=..40] ~ ~ ~ 5
kill @s