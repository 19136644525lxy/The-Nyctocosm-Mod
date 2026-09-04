#built using mc-build (https://github.com/mc-build/mc-build)

kill @e[tag=emissary,tag=flytarget]
execute at @s rotated ~ -35 positioned ^ ^ ^100 run summon marker ~ ~ ~ {Tags:["emissary","flytarget"]}
function emissary:attacks/phase4/grapple/deactivate
tag @s add grapplegrabbed
function animated_java:ajemissary/animations/pause_all
scoreboard players set #tween_duration aj.i 2
function animated_java:ajemissary/animations/phase4.attack.grapple_grab/tween_play
execute at @e[tag=emissary,tag=locator,tag=aj.ajemissary.locator.rightmiddlewingtip3,limit=1] run summon armor_stand ~ ~ ~ {Marker:1b,Invisible:1b,Tags:["emissary","attack","grapple","grapplepoint"]}
execute as @a[distance=..5] run function emissary:attacks/phase4/grapple/__generated__/block/729