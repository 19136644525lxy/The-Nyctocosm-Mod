#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s timer 1
kill @s[scores={timer=30..}]
kill @s[scores={timer=15..},tag=shortlived]
tp @s ^ ^ ^1
particle dust 1 0 0 2 ~ ~ ~ 0 0.1 0.1 0.1 2 force
particle dust 0 0 0 2.5 ~ ~ ~ 0 0 0 0 2 force
scoreboard players remove @s e.veintwist 1
execute if score @s e.veintwist matches ..0 run function effects:nightmare_vein/__generated__/block/61