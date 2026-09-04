#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players remove @s[tag=!s.casting] s.duration 1
execute as @s if score @s s.duration matches ..0 run function spells:__generated__/block/5
scoreboard players operation TempID value = @s s.planeID
execute as @a if score @s s.planeID = TempID value run tag @s add s.matchPlaneID
execute at @a[tag=s.matchPlaneID,limit=1] run tp @s ~ ~ ~ ~ 0
tag @a remove s.matchPlaneID