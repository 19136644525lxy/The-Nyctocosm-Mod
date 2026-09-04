scoreboard players operation @s aj.anim_time = @s aj.animation.inculcate.local_anim_time
function zzz_inculcate_internal:animations/inculcate/apply_frame_as_root
scoreboard players add @s aj.animation.inculcate.local_anim_time 1
execute if score @s aj.animation.inculcate.local_anim_time matches 111.. run function zzz_inculcate_internal:animations/inculcate/end