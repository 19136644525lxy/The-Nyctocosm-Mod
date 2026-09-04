#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation TempID value = @s s.planeID
execute as @e[tag=spell,tag=origin] if score @s s.planeID = TempID value run tag @s add s.matchPlaneID
execute at @e[tag=s.matchPlaneID,limit=1] rotated as @s run tp @s ~ ~ ~ ~ ~
tag @e remove s.matchPlaneID