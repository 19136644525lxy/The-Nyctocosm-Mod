#built using mc-build (https://github.com/mc-build/mc-build)

execute as @e[tag=wing,tag=emissary,tag=locator,tag=phase1] at @s run function emissary:attacks/phase2/wing_blast/__generated__/block/349
execute as @e[tag=attack,tag=emissary,tag=wing_blast] run function emissary:attacks/phase2/wing_blast/__generated__/block/350
playsound nyctocosm:emissary.attack.wing_blast.eyes master @a[distance=..300] ~ ~ ~ 50