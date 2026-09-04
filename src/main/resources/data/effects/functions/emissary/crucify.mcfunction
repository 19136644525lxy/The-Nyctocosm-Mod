#built using mc-build (https://github.com/mc-build/mc-build)

particle explosion_emitter ~ ~ ~ 0 0 0 0 1 force
particle flash ~ ~ ~ 0 0 0 0 1 force
execute rotated ~ 0 run function effects:emissary/__generated__/block/82
playsound nyctocosm:emissary.death.crucify master @a[distance=..300] ~ ~ ~ 50