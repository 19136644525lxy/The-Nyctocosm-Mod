#built using mc-build (https://github.com/mc-build/mc-build)

summon marker ~ ~ ~ {Tags:["halobeam","sorcererattack","justsummoned","spell"]}
execute as @e[tag=halobeam,tag=justsummoned] run function sorcerer:attacks/halobeam/__generated__/block/8
playsound nyctocosm:sorcerer.halobeam.summon master @a[distance=..30] ~ ~ ~ 5