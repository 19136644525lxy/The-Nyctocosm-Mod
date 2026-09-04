#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s positioned ~ ~2 ~ run particle flash ~ ~ ~ 0 0 0 0 1 force
execute positioned ^ ^2 ^-1 rotated ~ 90 run function effects:sorcerer/dashfly2
execute positioned ^ ^2 ^-3 rotated ~ 90 run function effects:sorcerer/dashfly2
execute facing entity @a[distance=..30,gamemode=!spectator,limit=1,sort=nearest] feet rotated ~ 0 run tp @s ~ ~ ~ ~ ~
execute at @a[distance=..30,gamemode=!spectator,limit=1,sort=nearest] run summon marker ~ ~ ~ {Tags:["airdash","dashtarget","justsummoned"]}
execute as @e[tag=dashtarget,tag=justsummoned] at @s run function sorcerer:attacks/airdash/__generated__/block/102
playsound nyctocosm:sorcerer.airdash.aim master @a[distance=..30] ~ ~ ~ 5