#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add summoned
tag @s add Plane
tag @s add spellcircle
tag @s add mobile
function ajmobilespellplane:animations/cast/play
scoreboard players operation @s s.planeID = s.global s.planeID