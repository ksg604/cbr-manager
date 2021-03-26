def differentiate_key_value(dict_a, dict_b):
    diff = {}
    for (k1, v1), (k2, v2) in zip(dict_a.items(), dict_b.items()):
        if k1 == k2 and v1 != v2:
            diff[k1] = v1
    return diff  # returns difference of A - B


def update_object(obj, **kwargs):
    for k, v in kwargs.items():
        setattr(obj, k, v)
    obj.save()
