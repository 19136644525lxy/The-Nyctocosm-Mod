#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s timer 1
kill @s[scores={timer=30..},tag=thin]
particle dust 0.741 0.000 1.000 1 ~ ~ ~ 0 0.1 0.1 0.1 2 force
particle dust 0 0 0 1 ~ ~ ~ 0 0 0 0 2 force
scoreboard players remove @s e.veintwist 1
execute if score @s e.veintwist matches ..0 run function effects:void_vein/__generated__/block/71
tp @s ^ ^ ^0.5