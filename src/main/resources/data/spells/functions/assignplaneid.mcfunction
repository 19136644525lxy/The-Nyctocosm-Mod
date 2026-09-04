#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set s.global s.planeID 0
execute as @a if score @s s.planeID matches 0 run tag @s add s.planeIDMatch
function spells:__generated__/while/0
scoreboard players operation @s s.planeID = s.global s.planeID
tag @a remove s.planeIDMatch