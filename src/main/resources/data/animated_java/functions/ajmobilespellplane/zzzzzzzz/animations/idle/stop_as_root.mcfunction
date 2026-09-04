scoreboard players set @s aj.ajmobilespellplane.animation.idle.local_anim_time 0
tag @s remove aj.ajmobilespellplane.animation.idle
execute on passengers run data modify entity @s interpolation_duration set value 0
tag @s add aj.ajmobilespellplane.disable_command_keyframes
function animated_java:ajmobilespellplane/zzzzzzzz/animations/idle/tree/leaf_0
tag @s remove aj.ajmobilespellplane.disable_command_keyframes