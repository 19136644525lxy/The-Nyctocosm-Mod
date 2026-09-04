scoreboard players set @s aj.ajspellplane.animation.fizzle.local_anim_time 0
tag @s remove aj.ajspellplane.animation.fizzle
execute on passengers run data modify entity @s interpolation_duration set value 0
tag @s add aj.ajspellplane.disable_command_keyframes
function animated_java:ajspellplane/zzzzzzzz/animations/fizzle/tree/leaf_0
tag @s remove aj.ajspellplane.disable_command_keyframes