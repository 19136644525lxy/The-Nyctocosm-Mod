scoreboard players set @s aj.ajsorcerer.animation.initial_spawn.local_anim_time 0
tag @s remove aj.ajsorcerer.animation.initial_spawn
execute on passengers run data modify entity @s interpolation_duration set value 0
tag @s add aj.ajsorcerer.disable_command_keyframes
function animated_java:ajsorcerer/zzzzzzzz/animations/initial_spawn/tree/leaf_0
tag @s remove aj.ajsorcerer.disable_command_keyframes