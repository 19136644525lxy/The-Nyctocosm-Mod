scoreboard players set @s aj.ajsorcererm1.animation.shardhail.local_anim_time 0
tag @s remove aj.ajsorcererm1.animation.shardhail
execute on passengers run data modify entity @s interpolation_duration set value 0
tag @s add aj.ajsorcererm1.disable_command_keyframes
function animated_java:ajsorcererm1/zzzzzzzz/animations/shardhail/tree/leaf_0
tag @s remove aj.ajsorcererm1.disable_command_keyframes