#built using mc-build (https://github.com/mc-build/mc-build)

effect clear @s darkness
effect clear @s blindness
effect give @s glowing 1000000 0 true
scoreboard players remove @s luminoustimer 1
execute if score @s luminoustimer matches ..0 run function spells:__generated__/block/183
summon marker ~ ~ ~ {Tags:["spell","luminescence","light_marker"]}