scoreboard players operation @s aj.anim_time = @s aj.animation.ez.local_anim_time
function zzz_test_internal:animations/ez/apply_frame_as_root
scoreboard players add @s aj.animation.ez.local_anim_time 1
execute if score @s aj.animation.ez.local_anim_time matches 20.. run function zzz_test_internal:animations/ez/end