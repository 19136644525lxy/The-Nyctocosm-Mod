#built using mc-build (https://github.com/mc-build/mc-build)

summon marker ~ ~ ~ {Tags:["emissary","attack","meteor_shower","shooter","justsummoned"]}
execute as @e[tag=justsummoned] at @s run function emissary:attacks/phase1/meteor_shower/__generated__/block/74
playsound nyctocosm:emissary.attack.meteor_shower.activate master @a[distance=..300] ~ ~ ~ 50