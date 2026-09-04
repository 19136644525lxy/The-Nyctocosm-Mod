scoreboard players set @s aj.ajsorcererm2.animation.dashstrike.local_anim_time 0
tag @s remove aj.ajsorcererm2.animation.dashstrike
execute on passengers run data modify entity @s interpolation_duration set value 0
tag @s add aj.ajsorcererm2.disable_command_keyframes
function animated_java:ajsorcererm2/zzzzzzzz/animations/dashstrike/tree/leaf_0
tag @s remove aj.ajsorcererm2.disable_command_keyframes