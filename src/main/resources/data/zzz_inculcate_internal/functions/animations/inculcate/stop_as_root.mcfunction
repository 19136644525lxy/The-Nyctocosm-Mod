scoreboard players set @s aj.animation.inculcate.local_anim_time 0
tag @s remove aj.inculcate.animation.inculcate
execute on passengers run data modify entity @s interpolation_duration set value 0
tag @s add aj.inculcate.disable_command_keyframes
function zzz_inculcate_internal:animations/inculcate/tree/leaf_0
tag @s remove aj.inculcate.disable_command_keyframes