#built using mc-build (https://github.com/mc-build/mc-build)

function emissary:attacks/phase2/celestial_smite/raycast
execute rotated ~ 0 run function effects:shockwave/meteor_shower
particle explosion_emitter ~ ~ ~ 0 0 0 0 1 force
playsound nyctocosm:emissary.attack.celestial_smite.shoot master @a[distance=..300] ~ ~ ~ 50
kill @s