scoreboard players set @s aj.ajhypertrace.animation.idle.local_anim_time 0
tag @s remove aj.ajhypertrace.animation.idle
execute on passengers run data modify entity @s interpolation_duration set value 0
tag @s add aj.ajhypertrace.disable_command_keyframes
function animated_java:ajhypertrace/zzzzzzzz/animations/idle/tree/leaf_0
tag @s remove aj.ajhypertrace.disable_command_keyframes