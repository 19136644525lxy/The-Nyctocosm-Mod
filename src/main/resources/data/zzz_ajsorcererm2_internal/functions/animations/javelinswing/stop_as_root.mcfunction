scoreboard players set @s aj.ajsorcererm2.animation.javelinswing.local_anim_time 0
tag @s remove aj.ajsorcererm2.animation.javelinswing
execute on passengers run data modify entity @s interpolation_duration set value 0
tag @s add aj.ajsorcererm2.disable_command_keyframes
function zzz_ajsorcererm2_internal:animations/javelinswing/tree/leaf_0
tag @s remove aj.ajsorcererm2.disable_command_keyframes