scoreboard players operation @s aj.id = .aj.last_id aj.id
tag @s remove aj.new
function #ajdeathwalker:on_summon_as_rig_entities
execute if entity @s[tag=aj.ajdeathwalker.bone] run function zzz_ajdeathwalker_internal:summon/as_bones
execute if entity @s[tag=aj.ajdeathwalker.locator] run function zzz_ajdeathwalker_internal:summon/as_locators
execute if entity @s[tag=aj.ajdeathwalker.camera] run function zzz_ajdeathwalker_internal:summon/as_cameras