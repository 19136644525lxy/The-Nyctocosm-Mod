#built using mc-build (https://github.com/mc-build/mc-build)

summon marker ~ ~ ~ {Tags:["voidmeteor","sorcererattack","meteor","spell","justsummoned"]}
execute as @e[tag=meteor,tag=justsummoned] run function sorcerer:attacks/voidmeteor/__generated__/block/122
playsound nyctocosm:sorcerer.voidmeteor.shoot master @a[distance=..50] ~ ~ ~ 5