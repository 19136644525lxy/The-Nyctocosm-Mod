#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s timer 1
kill @s[scores={timer=30..}]
kill @s[scores={timer=15..},tag=shortlived]
tp @s ^ ^ ^1
particle wax_on ~ ~ ~ 0.1 0.1 0.1 0.05 3 force
particle end_rod ~ ~ ~ 0.1 0.1 0.1 0.05 3 force
particle flame ~ ~ ~ 0.1 0.1 0.1 0.05 3 force
scoreboard players remove @s e.veintwist 1
execute if score @s e.veintwist matches ..0 run function effects:emissary/__generated__/block/76