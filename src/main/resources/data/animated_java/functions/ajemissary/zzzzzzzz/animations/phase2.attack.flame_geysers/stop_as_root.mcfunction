scoreboard players set @s aj.ajemissary.animation.phase2.attack.flame_geysers.local_anim_time 0
tag @s remove aj.ajemissary.animation.phase2.attack.flame_geysers
execute on passengers run data modify entity @s interpolation_duration set value 0
tag @s add aj.ajemissary.disable_command_keyframes
function animated_java:ajemissary/zzzzzzzz/animations/phase2.attack.flame_geysers/tree/leaf_0
tag @s remove aj.ajemissary.disable_command_keyframes