#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s run summon marker ~ ~ ~ {Tags:["Fulminance","spell","lightningdraw"]}
scoreboard players remove @s spelltimer 1
execute if score @s spelltimer matches ..0 run kill @s
execute at @s run function spells:fulminancemovement
execute at @s[tag=!hit] run function spells:fulminancemovement
execute at @s[tag=!hit] run function spells:fulminancemovement
execute at @s[tag=!hit] run function spells:fulminancemovement
execute at @s[tag=!hit] run function spells:fulminancemovement
execute at @s[tag=!hit] run function spells:fulminancemovement
function damage:playermarkertag
execute as @e[tag=f.hit] at @s run function spells:__generated__/block/150
tag @a remove damagetemp
execute at @e[tag=lightningdraw,tag=Fulminance,distance=..20] facing entity @s feet run function effects:lightning/draw_spark
execute as @s[tag=hit] at @s run function spells:__generated__/block/156