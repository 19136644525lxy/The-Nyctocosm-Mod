scoreboard players set @s aj.ajnullpoint.animation.hurt.local_anim_time 0
tag @s remove aj.ajnullpoint.animation.hurt
execute on passengers run data modify entity @s interpolation_duration set value 0
tag @s add aj.ajnullpoint.disable_command_keyframes
function zzz_ajnullpoint_internal:animations/hurt/tree/leaf_0
tag @s remove aj.ajnullpoint.disable_command_keyframes