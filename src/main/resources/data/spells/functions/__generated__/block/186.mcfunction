#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players remove @s s.resistance_timer 1
particle end_rod ~ ~1 ~ 0.2 0.3 0.2 0 1
effect give @s resistance 1 0 true
attribute @s minecraft:generic.knockback_resistance base set 100
execute if score @s s.resistance_timer matches ..0 run function spells:__generated__/block/187