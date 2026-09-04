#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add s.global s.planeID 1
tag @a remove s.planeIDMatch
execute as @a if score @s s.planeID = s.global s.planeID run tag @s add s.planeIDMatch
scoreboard players set #WHILE_6 LANG_MC_INTERNAL 1
function spells:__generated__/while/0