scoreboard players operation @s aj.id = .aj.last_id aj.id
tag @s remove aj.new
function #ajhypertrace:on_summon_as_rig_entities
execute if entity @s[tag=aj.ajhypertrace.bone] run function zzz_ajhypertrace_internal:summon/as_bones
execute if entity @s[tag=aj.ajhypertrace.locator] run function zzz_ajhypertrace_internal:summon/as_locators
execute if entity @s[tag=aj.ajhypertrace.camera] run function zzz_ajhypertrace_internal:summon/as_cameras