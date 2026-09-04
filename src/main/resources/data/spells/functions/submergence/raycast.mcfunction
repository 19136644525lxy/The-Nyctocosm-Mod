#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add RAYCAST value 1
execute if score RAYCAST value matches 60.. run tag @s add endraycast
execute if entity @e[tag=matchedid,tag=tendril,distance=..1] run tag @s add endraycast
particle soul_fire_flame ~ ~ ~ 0 0 0 0.01 2 force
execute as @s[tag=!endraycast] positioned ^ ^ ^0.5 run function spells:submergence/raycast
execute as @s[tag=endraycast] run function spells:submergence/__generated__/block/405