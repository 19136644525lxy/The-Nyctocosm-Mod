#built using mc-build (https://github.com/mc-build/mc-build)

execute positioned ^ ^ ^-3 run tag @a[distance=..3] add s.immoveable
scoreboard players remove @s spelltimer 1
execute if score @s s.damage matches ..0 run scoreboard players set @s spelltimer 0
execute if score @s spelltimer matches ..0 run function spells:__generated__/block/85