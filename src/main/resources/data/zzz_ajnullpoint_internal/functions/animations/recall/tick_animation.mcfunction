scoreboard players add @s aj.ajnullpoint.animation.recall.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajnullpoint.animation.recall.local_anim_time
function zzz_ajnullpoint_internal:animations/recall/apply_frame_as_root
execute if score @s aj.ajnullpoint.animation.recall.local_anim_time matches 25.. run function zzz_ajnullpoint_internal:animations/recall/end