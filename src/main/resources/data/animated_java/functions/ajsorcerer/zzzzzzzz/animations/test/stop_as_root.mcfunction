scoreboard players set @s aj.ajsorcerer.animation.test.local_anim_time 0
tag @s remove aj.ajsorcerer.animation.test
execute on passengers run data modify entity @s interpolation_duration set value 0
tag @s add aj.ajsorcerer.disable_command_keyframes
function animated_java:ajsorcerer/zzzzzzzz/animations/test/tree/leaf_0
tag @s remove aj.ajsorcerer.disable_command_keyframes