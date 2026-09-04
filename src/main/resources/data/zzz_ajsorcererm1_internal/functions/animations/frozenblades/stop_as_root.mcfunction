scoreboard players set @s aj.ajsorcererm1.animation.frozenblades.local_anim_time 0
tag @s remove aj.ajsorcererm1.animation.frozenblades
execute on passengers run data modify entity @s interpolation_duration set value 0
tag @s add aj.ajsorcererm1.disable_command_keyframes
function zzz_ajsorcererm1_internal:animations/frozenblades/tree/leaf_0
tag @s remove aj.ajsorcererm1.disable_command_keyframes