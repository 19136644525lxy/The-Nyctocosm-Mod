#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players remove @s e.shaketimer 1
execute if score @s e.shaketimer matches ..0 run function effects:screen_shake/__generated__/block/619
execute at @s run function effects:screen_shake/shake